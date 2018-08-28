import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-drawbox',
  templateUrl: './drawbox.component.html',
  styleUrls: ['./drawbox.component.css']
})
export class DrawboxComponent implements OnInit {

  @ViewChild ('DrawBox') drawbox: ElementRef;

  constructor() { }

  ngOnInit() {
  }

}
