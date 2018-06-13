import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {ButtonBinder} from '../../../../../../../../malai-core/org.malai.ts/src';
import {AnonCmd} from '../../../../../../../../malai-core/org.malai.ts/src/src-core/command/AnonCmd';
import {MenuButtonComponent} from '../menu-button.component';


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
