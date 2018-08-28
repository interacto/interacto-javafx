import {CommandImpl, SrcTgtPointsData} from 'org.malai.ts-dev';

export class DrawOnuUdate extends CommandImpl {

  private tgtData: SrcTgtPointsData;
  private parent: SVGElement;

  public constructor (tgtData: SrcTgtPointsData) {
    super();
    this.tgtData = tgtData;
  }
  canDo(): boolean {
    return true;
  }

  protected doCmdBody(): void {
    this.parent = <SVGElement>this.tgtData.getCurrentTarget().get();
    const currentTarget = <SVGCircleElement>this.tgtData.getTgtObject().get();
    const child = <typeof currentTarget>this.parent.childNodes.item(0);
    child.setAttribute('cx', this.tgtData.getTgtClientX().toString());
    child.setAttribute('cy', this.tgtData.getTgtClientY().toString());
    child.setAttribute('transform', 'translate(-10 -10)');
    if (this.parent.childNodes.item(1) !== null) {
      const child_text = <typeof currentTarget>this.parent.childNodes.item(1);
      child_text.setAttribute('x', this.tgtData.getTgtClientX().toString());
      child_text.setAttribute('y', this.tgtData.getTgtClientY().toString());
      child_text.setAttribute('transform', 'translate(-10 -10)');
    }
  }
}
