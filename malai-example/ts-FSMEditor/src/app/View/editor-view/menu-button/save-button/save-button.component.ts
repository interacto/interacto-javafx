import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {ButtonBinder} from '../../../../../../../../malai-core/org.malai.ts/src';
import {AnonCmd} from '../../../../../../../../malai-core/org.malai.ts/src/src-core/command/AnonCmd';
import {MenuButtonComponent} from '../menu-button.component';

@Component({
  selector: 'app-save-button',
  templateUrl: './save-button.component.html',
  styleUrls: ['./save-button.component.css']
})
export class SaveButtonComponent extends MenuButtonComponent implements OnInit {

  @ViewChild ('ButtonSave') elm: ElementRef;

  ngOnInit() {
    new ButtonBinder<AnonCmd>(() => new AnonCmd(() => {
      alert('Save the current FSM.');
    })).on(this.elm.nativeElement).bind();
  }
}
