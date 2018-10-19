
package com.test.e4.application.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TestPart {

	@PostConstruct
	public void postConstruct(Composite parent) {
		// Enable a label as a Drag Source
		final Label dragLabel = new Label(parent, SWT.BORDER);
		dragLabel.setText("text to be transferred");
		// Allow data to be copied or moved from the drag source
		int operations = DND.DROP_MOVE | DND.DROP_COPY;
		DragSource source = new DragSource(dragLabel, operations);
		// Provide data in Text format
		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		source.setTransfer(types);
		source.addDragListener(new DragSourceListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 732050553955470580L;

			public void dragStart(DragSourceEvent event) {
				// Only start the drag if there is actually text in the
				// label - this text will be what is dropped on the target.
				if (dragLabel.getText().length() == 0) {
					event.doit = false;
				}
			}

			public void dragSetData(DragSourceEvent event) {
				// Provide the data of the requested type.
				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					event.data = dragLabel.getText();
				}
			}

			public void dragFinished(DragSourceEvent event) {
				// If a move operation has been performed, remove the data
				// from the source
				if (event.detail == DND.DROP_MOVE) {
					dragLabel.setText("");
				}
			}

		});

		// Enable a table as a Drop Target
		final Table dropTable = new Table(parent, SWT.BORDER);
		dropTable.setLinesVisible(true);
		dropTable.setHeaderVisible(true);
		for (int i = 0; i < 10; i++) {
			TableItem item = new TableItem(dropTable, SWT.NONE);
			item.setText("item" + i);
		}
		// Allow data to be copied or moved to the drop target
		operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget target = new DropTarget(dropTable, operations);
		// Receive data in Text or File format
		final TextTransfer textTransfer = TextTransfer.getInstance();
		final FileTransfer fileTransfer = FileTransfer.getInstance();
		types = new Transfer[] { fileTransfer, textTransfer };
		target.setTransfer(types);
		target.addDropListener(new DropTargetListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1833488927624472359L;

			public void dragEnter(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					if ((event.operations & DND.DROP_COPY) != 0) {
						event.detail = DND.DROP_COPY;
					} else {
						event.detail = DND.DROP_NONE;
					}
				}
				// will accept text but prefer to have files dropped
				for (int i = 0; i < event.dataTypes.length; i++) {
					if (fileTransfer.isSupportedType(event.dataTypes[i])) {
						event.currentDataType = event.dataTypes[i];
						// files should only be copied
						if (event.detail != DND.DROP_COPY) {
							event.detail = DND.DROP_NONE;
						}
						break;
					}
				}
			}

			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;
				if (textTransfer.isSupportedType(event.currentDataType)) {
					// NOTE: on unsupported platforms this will return null
					Object o = textTransfer.nativeToJava(event.currentDataType);
					String t = (String) o;
					if (t != null)
						System.out.println(t);
				}
			}

			public void dragOperationChanged(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					if ((event.operations & DND.DROP_COPY) != 0) {
						event.detail = DND.DROP_COPY;
					} else {
						event.detail = DND.DROP_NONE;
					}
				}
				// allow text to be moved but files should only be copied
				if (fileTransfer.isSupportedType(event.currentDataType)) {
					if (event.detail != DND.DROP_COPY) {
						event.detail = DND.DROP_NONE;
					}
				}
			}

			public void dragLeave(DropTargetEvent event) {
			}

			public void dropAccept(DropTargetEvent event) {
			}

			public void drop(DropTargetEvent event) {
				if (textTransfer.isSupportedType(event.currentDataType)) {
					String text = (String) event.data;
					TableItem item = new TableItem(dropTable, SWT.NONE);
					item.setText(text);
				}
				if (fileTransfer.isSupportedType(event.currentDataType)) {
					String[] files = (String[]) event.data;
					for (int i = 0; i < files.length; i++) {
						TableItem item = new TableItem(dropTable, SWT.NONE);
						item.setText(files[i]);
					}
				}
			}
			
		});
	}

}
