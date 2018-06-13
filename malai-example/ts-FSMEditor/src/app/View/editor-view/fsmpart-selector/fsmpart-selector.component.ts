import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-fsmpart-selector',
  templateUrl: './fsmpart-selector.component.html',
  styleUrls: ['./fsmpart-selector.component.css']
})
export class FSMpartSelectorComponent implements OnInit {

  @Input () name: string;

  constructor() { }

  ngOnInit() {
  }

}
