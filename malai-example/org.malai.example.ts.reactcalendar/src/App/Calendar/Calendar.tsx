import * as React from "react";

import {AnonCmd, buttonBinder} from "org.malai.ts-dev";
import {RefObject} from "react";
import './Calendar.css';
import {Day} from "./Day/Day";

interface IWeekProps {
    weekNumber: number[];
}

interface IMonthState {
    monthValue: number;
    monthName: string;
}

const WeekRender: React.SFC<IWeekProps> = (props) => {
    return <tr className="calendar-row">
        <td><Day dayValue={props.weekNumber[0]}/></td>
        <td><Day dayValue={props.weekNumber[1]}/></td>
        <td><Day dayValue={props.weekNumber[2]}/></td>
        <td><Day dayValue={props.weekNumber[3]}/></td>
        <td><Day dayValue={props.weekNumber[4]}/></td>
        <td><Day dayValue={props.weekNumber[5]}/></td>
        <td><Day dayValue={props.weekNumber[6]}/></td>
    </tr>;
};

export class Calendar extends React.Component<{}, IMonthState>{

    private readonly refLeftButton: RefObject<HTMLButtonElement>;
    private readonly refRightButton: RefObject<HTMLButtonElement>;

    private lastIndiceMonth: number;
    private oldLastIndiceMonth: number;
    private toLastMonth: boolean;

    private indiceMonth: number;


    private year: Array<[number, string]> = [[31, "janvier"],[28,"février"],[31,"mars"],[30,"avril"],[31,"mai"],[30,"juin"],[31,"juillet"],[31,"août"], [30,"septembre"],[31,"octobre"],[30,"novembre"],[31,"décembre"]];

    public constructor(props: {}) {
        super(props);
        this.refLeftButton = React.createRef();
        this.refRightButton = React.createRef();
        this.lastIndiceMonth = 1;
        this.oldLastIndiceMonth = 0;
        this.indiceMonth = 0;
        this.toLastMonth = false;
        this.state = {monthValue : this.year[this.indiceMonth][0], monthName : this.year[this.indiceMonth][1]};
    }

    public componentDidMount() {
        buttonBinder<AnonCmd>(i => new AnonCmd(() => {
            this.toLastMonth = true;
            this.indiceMonth = this.indiceMonth - 1;
                if (this.indiceMonth < 0) {
                    this.indiceMonth = 11;
                }
                const lastMonth = this.indiceMonth;
                this.setState({monthValue : this.year[lastMonth][0], monthName : this.year[lastMonth][1]});
            })).on(this.refLeftButton.current)
            .bind();
        buttonBinder<AnonCmd>(i => new AnonCmd(() => {
            this.indiceMonth = this.indiceMonth + 1;
            if (this.indiceMonth === 12) {
                this.indiceMonth = 0;
            }
            const  nextMonth = this.indiceMonth;
            this.setState({monthValue: this.year[nextMonth][0], monthName: this.year[nextMonth][1]});
        })).on(this.refRightButton.current).bind();
    }

    public monthrender(monthLenght: number) {
        let getLastMonth: boolean = false;

        const newMonth = new Array<number[]>();
        if (this.toLastMonth) {
            console.log(this.oldLastIndiceMonth);
            this.lastIndiceMonth = this.oldLastIndiceMonth;
            this.toLastMonth = false;
        }
        let dayIndice = 1;
        for (let indexMonth = 0; indexMonth < 6; indexMonth++) {
            const newWeek: number[] = new Array<number>();
            for (let indexWeek = 0; indexWeek < 7; indexWeek++) {
                    if (indexMonth === 0) {
                        if (indexWeek < this.lastIndiceMonth) {
                            newWeek[indexWeek] = -1;
                        } else {
                            if (dayIndice !== monthLenght + 1) {
                                newWeek[indexWeek] = dayIndice;
                                dayIndice++;
                            }
                        }
                    } else {
                        if (dayIndice !== monthLenght + 1) {
                        newWeek[indexWeek] = dayIndice;
                        dayIndice++;
                        } else {
                        newWeek[indexWeek] = -1;
                        if (!getLastMonth) {
                            this.lastIndiceMonth = indexWeek;
                            getLastMonth = true;
                        }
                        }
                    }
                }
                newMonth.push(newWeek);
            }
            if (!newMonth[5]) {
                return([
                    <WeekRender key={1} weekNumber={newMonth[0]}/>,
                    <WeekRender key={2} weekNumber={newMonth[1]}/>,
                    <WeekRender key={3} weekNumber={newMonth[2]}/>,
                    <WeekRender key={4} weekNumber={newMonth[3]}/>,
                    <WeekRender key={5} weekNumber={newMonth[4]}/>,
                ]);
            } else {
                return( [
                        <WeekRender key={1} weekNumber={newMonth[0]}/>,
                        <WeekRender key={2} weekNumber={newMonth[1]}/>,
                        <WeekRender key={3} weekNumber={newMonth[2]}/>,
                        <WeekRender key={4} weekNumber={newMonth[3]}/>,
                        <WeekRender key={5} weekNumber={newMonth[4]}/>,
                        <WeekRender key={6} weekNumber={newMonth[5]}/>,
                    ]
                );
            }
    }

    public render() {
        return( [
            <span key={0}>{this.state.monthName}</span>,
                <div key={1} className="global-wrapper">
                    <button id="button-left" ref={this.refLeftButton}><span> Mois d'avant</span></button>
                    <table className="calendar-body">
                        <tbody>
                            <tr className="day-name">
                                <th className="day">Mon</th>
                                <th className="day">Tue</th>
                                <th className="day">Wed</th>
                                <th className="day">Thu</th>
                                <th className="day">Fri</th>
                                <th className="day">Sat</th>
                                <th className="day">Sun</th>
                            </tr>
                        </tbody>
                        <tbody>
                            {this.monthrender(this.state.monthValue)}
                        </tbody>
                    </table>
                    <button id="button-right" ref={this.refRightButton}><span> Mois d'après </span></button>
                </div>
        ]
        );
    }
}