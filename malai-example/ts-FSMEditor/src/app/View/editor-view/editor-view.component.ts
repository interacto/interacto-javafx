import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FSMpartSelectorComponent} from './fsmpart-selector/fsmpart-selector.component';
import {DragLock, nodeBinder, SrcTgtPointsData, dndBinder, LogLevel} from 'org.malai.ts-dev';
import {DrawboxComponent} from './DrawBox/drawbox/drawbox.component';
import {DrawPartGroup} from '../../Command/DnD_part_editor/draw-part_group';
import {DrawOnuUdate} from '../../Command/DnD_part_editor/draw_on_update';
import {DrawPath} from '../../Command/DnD_part_editor/draw-path';


@Component({
  selector: 'app-editor-view',
  templateUrl: './editor-view.component.html',
  styleUrls: ['./editor-view.component.css']
})
export class EditorViewComponent implements OnInit, AfterViewInit {
  constructor() { }

  @ViewChild ('FSMselector') fsmSelector: FSMpartSelectorComponent;
  @ViewChild ('DrawBox') drawbox: DrawboxComponent;

  ngOnInit(): void {
  }

  ngAfterViewInit() {

    dndBinder<DrawPartGroup>( i => new DrawPartGroup(i), false, false)
      .on(this.fsmSelector.parts).to(this.drawbox.drawbox.nativeElement)
      .bind();

    dndBinder<DrawOnuUdate>( i => new DrawOnuUdate(i), false, false)
      .onContent(this.drawbox.drawbox.nativeElement).to(this.drawbox.drawbox.nativeElement).log(LogLevel.INTERACTION).bind();

    let drawPath: DrawPath;
    nodeBinder<SrcTgtPointsData, DrawPath, DragLock>(new DragLock(), i => {
      console.log(i.getSrcObject().get());
      console.log(i.getTgtObject().get());
      return drawPath =  new DrawPath(i.getSrcClientX(), i.getSrcClientY(), this.drawbox.drawbox.nativeElement);
    })
      .on(this.drawbox.drawbox.nativeElement)
      .log(LogLevel.INTERACTION)
      .end(i => drawPath.setTargetCoord(i.getTgtClientX(), i.getTgtClientY()))
      .bind();
  }
}
