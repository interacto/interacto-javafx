import { Component, OnInit } from '@angular/core';

import {parseString} from 'xml2js';

@Component({
  selector: 'app-xml-view',
  templateUrl: './xml-view.component.html',
  styleUrls: ['./xml-view.component.css']
})
export class XmlViewComponent implements OnInit {

  xmlString: string;

  constructor() { }

   ngOnInit() {
  //   this.xmlString = parseString('./assets/FSMsave/test.xml', function (err, result) {
  //     // console.dir(result);
  //   });
   }

}
