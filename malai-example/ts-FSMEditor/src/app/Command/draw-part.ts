import {CommandImpl, SrcTgtPointsData} from '../../../../../malai-core/org.malai.ts/src';

export class DrawPart extends CommandImpl {

  private tgtData: SrcTgtPointsData;
  private tgtElem: HTMLCanvasElement;

  public constructor (tgtData: SrcTgtPointsData) {
    super();
    this.tgtData = tgtData;
  }

  protected doCmdBody(): void {
    this.tgtElem = <HTMLCanvasElement>this.tgtData.getTgtObject().get();
    const src: HTMLImageElement = <HTMLImageElement> this.tgtData.getSrcObject().get();
    const clone = src.cloneNode(true);
    const wrapper = document.createElement('div');
    wrapper.setAttribute('class', 'part');
    console.log('Top = ' + this.tgtData.getTgtClientX() + ' Left = ' + this.tgtData.getTgtClientY());
    wrapper.setAttribute('style', ' position: absolute; width: 200px;' +
      'height: 150px; top: ' + this.tgtData.getTgtClientX() + 'px; left: ' + this.tgtData.getTgtClientY() + 'px;');
    wrapper.appendChild(clone);
    this.tgtElem.appendChild(wrapper);
  }

  canDo(): boolean {
    return true;
  }
}
