import {Component, Input, OnInit} from '@angular/core';
import {TabsComponent} from '../tabs.component';

@Component({
  selector: 'app-tab',
  templateUrl: './tab.component.html',
  styleUrls: ['./tab.component.css']
})
export class TabComponent implements OnInit {

  @Input () name: string;

  active: boolean;

  constructor(tabs: TabsComponent) {
    tabs.addTab(this);
  }

  ngOnInit() {
  }

}
