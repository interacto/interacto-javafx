import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {MenuButtonComponent} from '../menu-button.component';
import {AnonCmd, ButtonBinder} from 'org.malai.ts-dev';


@Component({
  selector: 'app-new-button',
  templateUrl: './new-button.component.html',
  styleUrls: ['./new-button.component.css']
})
export class NewButtonComponent extends MenuButtonComponent implements OnInit {

  @ViewChild ('ButtonNew') elm: ElementRef;

  ngOnInit() {
    new ButtonBinder<AnonCmd>(() => new AnonCmd(() => {
      alert('Create a New FSM.');
    })).on(this.elm.nativeElement).bind();
  }
}
