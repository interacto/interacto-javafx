import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-menu-button',
  templateUrl: './menu-button.component.html',
  styleUrls: ['./menu-button.component.css']
})
export class MenuButtonComponent implements OnInit {

  @Input () name: string;

  constructor() { }

  ngOnInit() {
  }

}
