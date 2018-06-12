import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-fsmpart',
  templateUrl: './fsmpart.component.html',
  styleUrls: ['./fsmpart.component.css']
})
export class FSMpartComponent implements OnInit {

  @Input () src: string;

  @Input () name: string;

  constructor() { }

  ngOnInit() {
  }

}
