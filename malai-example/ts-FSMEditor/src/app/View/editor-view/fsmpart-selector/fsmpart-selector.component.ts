import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {FSMpartComponent} from './fsmpart/fsmpart.component';

@Component({
  selector: 'app-fsmpart-selector',
  templateUrl: './fsmpart-selector.component.html',
  styleUrls: ['./fsmpart-selector.component.css']
})
export class FSMpartSelectorComponent implements OnInit {

  @ViewChild ('Init') init_part: FSMpartComponent;
  @ViewChild ('Stnd') stnd_part: FSMpartComponent;
  @ViewChild ('Term') term_part: FSMpartComponent;
  @ViewChild ('Canc') canc_part: FSMpartComponent;


  @Input () name: string;

  constructor() { }

  ngOnInit() {
  }

}
