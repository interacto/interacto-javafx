import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {TabComponent} from './tab/tab.component';
import {Click, NodeBinder, PointData} from '../../../../../../malai-core/org.malai.ts/src';
import {AnonCmd} from '../../../../../../malai-core/org.malai.ts/src/src-core/command/AnonCmd';

@Component({
  selector: 'app-tabs',
  templateUrl: './tabs.component.html',
  styleUrls: ['./tabs.component.css']
})
export class TabsComponent implements OnInit {

  tabs: TabComponent[] = [];

  @ViewChild ('Editor') elm_editor: ElementRef;
  @ViewChild('XML') elm_xml: ElementRef;

  click = new Click();

  addTab(tab: TabComponent) {
    if (this.tabs.length === 0) {
      tab.active = true;
    }
    this.tabs.push(tab);
  }

  selectTab(tgt: HTMLLIElement) {
    const tgt_name = tgt.getAttribute('title');
    this.tabs.forEach((value) => {
      value.active = value.name === tgt_name;
    });
  }

  constructor() { }

  ngOnInit() {
    new NodeBinder<AnonCmd, Click, PointData>(this.click, () => new AnonCmd(() => {
      const tgt: HTMLLIElement = <HTMLLIElement>this.click.getData().getSrcObject().get();
      this.selectTab(tgt);
    })).on(this.elm_editor.nativeElement).on(this.elm_xml.nativeElement).bind();
  }

}
