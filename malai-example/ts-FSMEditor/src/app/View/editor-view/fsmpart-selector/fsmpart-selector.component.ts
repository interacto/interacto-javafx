import {AfterViewInit, Component, Directive, ElementRef, Input, OnInit, QueryList, ViewChildren} from '@angular/core';
import {MArray} from 'org.malai.ts-dev';

@Component({
  selector: 'app-fsmpart-selector',
  templateUrl: './fsmpart-selector.component.html',
  styleUrls: ['./fsmpart-selector.component.css']
})
export class FSMpartSelectorComponent implements AfterViewInit {

  @ViewChildren ('State') part: QueryList<ElementRef>;

  parts: MArray<EventTarget>;

  @Input () name: string;

  constructor() {
  }

  ngAfterViewInit(): void {
    this.parts = new MArray<EventTarget>();
    this.part.forEach(item => this.parts.push(item.nativeElement));
  }

}
