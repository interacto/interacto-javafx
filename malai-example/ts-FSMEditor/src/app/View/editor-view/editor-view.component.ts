import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NodeBinder, DnD, SrcTgtPointsData} from '../../../../../../malai-core/org.malai.ts/src';
import {FSMpartSelectorComponent} from './fsmpart-selector/fsmpart-selector.component';
import {DrawPart} from '../../Command/draw-part';


@Component({
  selector: 'app-editor-view',
  templateUrl: './editor-view.component.html',
  styleUrls: ['./editor-view.component.css']
})
export class EditorViewComponent implements OnInit {

  @ViewChild ('DrawBox') canvas: ElementRef;
  @ViewChild(FSMpartSelectorComponent) fsm_selector: FSMpartSelectorComponent;

  dnd: DnD;

  constructor() { }

  ngOnInit() {
    this.dnd = new DnD(false, false);
    new NodeBinder<DrawPart, DnD, SrcTgtPointsData>(this.dnd, () => new DrawPart(this.dnd.getData()))
      .on(this.fsm_selector.init_part.img.nativeElement).on(this.fsm_selector.stnd_part.img.nativeElement)
      .on(this.fsm_selector.term_part.img.nativeElement).on(this.fsm_selector.canc_part.img.nativeElement)
      .on(this.canvas.nativeElement).bind();
  }
}
