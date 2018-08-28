import {CommandImpl} from 'org.malai.ts-dev';

export class DrawPath extends CommandImpl {

  private readonly srcX: number;
  private readonly srcY: number;
  private tgtX: number;
  private tgtY: number;
  private canvas: SVGSVGElement;

  public constructor(srcX: number, srcY: number, canvas: SVGSVGElement) {
    super();
    this.srcX = srcX;
    this.srcY = srcY;
    this.canvas = canvas;
  }

  canDo(): boolean {
    return true;
  }

  public setTargetCoord(tgtX: number, tgtY: number) {
    console.log('test end');
    this.tgtX = tgtX;
    this.tgtY = tgtY;
  }

  protected doCmdBody(): void {
    const srcSvgPoint: SVGPoint = this.canvas.createSVGPoint();
    const tgtSvgPoint: SVGPoint = this.canvas.createSVGPoint();
    srcSvgPoint.x = this.srcX;
    srcSvgPoint.y = this.srcY;
    tgtSvgPoint.x = this.tgtX;
    tgtSvgPoint.y = this.tgtY;
    console.log('test');
    srcSvgPoint.matrixTransform(this.canvas.getScreenCTM().inverse());
    tgtSvgPoint.matrixTransform(this.canvas.getScreenCTM().inverse());
    console.log('test');
    const new_group = document.createElementNS('http://www.w3.org/2000/svg', 'g');
    const new_path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    new_path.setAttribute('d', 'M ' + srcSvgPoint.x + ' ' + srcSvgPoint.y
      + ' L ' + tgtSvgPoint.x + ' ' + tgtSvgPoint.y);
    new_path.setAttribute('stroke', '#000000');
    new_group.appendChild(new_path);
    this.canvas.appendChild(new_group);
    console.log('test');
  }
}
