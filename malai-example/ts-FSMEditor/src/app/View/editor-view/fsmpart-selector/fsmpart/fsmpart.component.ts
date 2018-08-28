import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-fsmpart',
  templateUrl: './fsmpart.component.html',
  styleUrls: ['./fsmpart.component.css']
})
export class FSMpartComponent implements OnInit {

  @Input () classType: string;

  @Input () styleFill: string;

  @Input () isText: boolean;

  @ViewChild ('Img') img: ElementRef;

  constructor() {
  }

  ngOnInit() {
  }

}
