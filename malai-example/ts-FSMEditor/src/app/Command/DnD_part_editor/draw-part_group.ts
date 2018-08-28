import {CommandImpl, SrcTgtPointsData} from 'org.malai.ts-dev';

export class DrawPartGroup extends CommandImpl {

  private tgtData: SrcTgtPointsData;
  private tgtElem: SVGSVGElement;


  public constructor (tgtData: SrcTgtPointsData) {
    super();
    this.tgtData = tgtData;
  }

  canDo(): boolean {
    return true;
  }

  protected doCmdBody(): void {
    this.tgtElem = <SVGSVGElement>this.tgtData.getTgtObject().get();
    const src: SVGGElement = <SVGGElement> this.tgtData.getCurrentTarget().get();
    const clone = <typeof src>src.cloneNode(true);
    const child = <typeof clone>clone.childNodes.item(0);
    child.setAttribute('cx', this.tgtData.getTgtClientX().toString());
    child.setAttribute('cy', this.tgtData.getTgtClientY().toString());
    child.setAttribute('transform', 'translate(-10 -10)');
    if (clone.childNodes.item(1) !== null) {
      const child_text = <typeof clone>clone.childNodes.item(1);
      child_text.setAttribute('x', this.tgtData.getTgtClientX().toString());
      child_text.setAttribute('y', this.tgtData.getTgtClientY().toString());
      child_text.setAttribute('transform', 'translate(-10 -10)');
    }
    this.tgtElem.appendChild(clone);
  }
}
