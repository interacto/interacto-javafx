import {Click, LogLevel, nodeBinder, PointData} from "org.malai.ts-dev";
import * as React from "react";
import {RefObject} from "react";
import {BasicLog} from "../../../Command/BasicLog";

interface IProps {
    dayValue: number | undefined;
}

export class Day extends React.Component<IProps>{

    private refDay: RefObject<HTMLDivElement>;

    public constructor(props: IProps) {
        super(props);
        this.refDay = React.createRef();
    }

    public componentDidMount() {
        const click = new Click();
        nodeBinder<PointData, BasicLog, Click>(click,i => new BasicLog(click.getData().getSrcObject().get())).on(this.refDay.current).log(LogLevel.INTERACTION).log(LogLevel.COMMAND).log(LogLevel.BINDING).bind();
    }

    public render() {

        let render;

        if (this.props.dayValue === -1) {
            render = <div className="no-display"/>;
        } else {
            render = <div className="day-component" ref={this.refDay}>
                <div className="day-display">{this.props.dayValue}</div>
                <div className="day-content"/>
            </div>
        }

        return ( render );
    }
}