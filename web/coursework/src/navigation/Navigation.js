import React from 'react';
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";

export default class Navigation extends React.Component {

    height = 600;
    width = 600;
    xUnit = this.width/40;
    yUnit = this.height/40;
    xCent = this.width/2;
    yCent = this.height/2;
    color = ["red", "blue", "green"];
    smallRoute = [];
    responseJson;

    constructor(props) {
        super(props);
        this.state = { from : '', to : '', showRoute: false, enoughLim: false, wrongStpg: false, block: false};
    }

    //componentDidMount() {
    //    this.updateRoute();
    //}

    updateRoute() {
        const ctx = this.refs.canvas.getContext('2d');
        ctx.clearRect(0, 0, this.width, this.height);

        // ctx.beginPath();
        // ctx.moveTo(0, this.yCent);
        // ctx.lineTo(this.width, this.yCent);
        // ctx.moveTo(this.xCent, 0);
        // ctx.lineTo(this.xCent, this.height);
        // ctx.stroke();

        this.smallRoute = [];
        let q = 0, c = 0;
        let c0 = this.responseJson[1][q].stoppageFrom, c1 = "", c2 = "";

        for (let i = 0; i < this.responseJson[1].length; i++) {
            if(this.responseJson[1][q].transport === "Пересаживаемся на другой номер маршрута" ||
                this.responseJson[1][q].transport === "Пересаживаемся на другой маршрут") {
                c1 = this.responseJson[1][q].stoppageFrom;
                c2 = this.responseJson[1][q-1].transport;
                this.smallRoute[c] = [c0, c1, c2];
                c++;
                c0 = this.responseJson[1][q].stoppageTo;
            } else {
                this.line(this.responseJson[1][q].xFrom, this.responseJson[1][q].yFrom, this.responseJson[1][q].xTo, this.responseJson[1][q].yTo, this.color[c]);
            }
            q++;
        }

        c1 = this.responseJson[1][this.responseJson[1].length-1].stoppageTo;
        c2 = this.responseJson[1][this.responseJson[1].length-1].transport;
        this.smallRoute[c] = [c0, c1, c2];

        q = 0;
        c = 0;
        for (let i = 0; i < this.responseJson[1].length; i++) {
            if(this.responseJson[1][q].transport === "Пересаживаемся на другой номер маршрута" ||
                this.responseJson[1][q].transport === "Пересаживаемся на другой маршрут") {
                c++;
            }
            else {
                this.circle(this.responseJson[1][q].xTo, this.responseJson[1][q].yTo);
            }
            q++;
        }
        this.circle(this.responseJson[1][0].xFrom, this.responseJson[1][0].yFrom);

        if (this.responseJson[0].text === "enough limit")
            this.setState({enoughLim: true});
        else
            this.setState({enoughLim: false});
    }

    circle(x, y) {
        const ctx = this.refs.canvas.getContext('2d');
        ctx.beginPath();
        ctx.strokeStyle = "black";
        ctx.lineWidth = 1;
        ctx.arc(this.xCent + x*this.xUnit, this.yCent - y*this.yUnit, 5, 0, Math.PI*2);
        ctx.moveTo(this.xCent + x*this.xUnit, this.yCent - y*this.yUnit);
        ctx.arc(this.xCent + x*this.xUnit, this.yCent - y*this.yUnit, 1, 0, Math.PI*2);
        ctx.stroke();
    }

    line(x1, y1, x2, y2, color) {
        const ctx = this.refs.canvas.getContext('2d');
        ctx.beginPath();
        ctx.strokeStyle = color;
        ctx.lineWidth = 3;
        ctx.moveTo(this.xCent + x1*this.xUnit, this.yCent - y1*this.yUnit);
        ctx.lineTo(this.xCent + x2*this.xUnit, this.yCent - y2*this.yUnit);
        ctx.stroke();
    }

    handleChange = (event) => {
        this.setState({block: true});
        this.setState({[event.target.name]: event.target.value});
    };

    showRoute = () => {
        this.setState({block: false});
        this.setState({showRoute: false});
        const {from, to} = this.state;
        if (from > 0 && from < 8422 && to > 0 && to < 8422) {
            fetch('api/route', {
                method: 'post',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    from: from,
                    to: to,
                    spend: 'no'
                })
            }).then(response => {
                console.log(response);
                return response.json();
            }).then(data => {
                this.responseJson = data;
                this.setState({showRoute: true, succRide: false});
                this.updateRoute();
            })
        } else {
            this.setState({showRoute: false, succRide: false});
        }
    };

    takeRide = () => {
        fetch('api/route', {
            method: 'post',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                from: this.state.from,
                to: this.state.to,
                spend: 'yes'
            })
        }).then(response => {
            return response.json();
        }).then(data => {
            this.setState({succRide: true});
        })
    };

    render() {
        return (
            <center>
                <table>
                    <tr>
                        <td>
                            <canvas className="canvas" ref="canvas" width={this.width} height={this.height} />
                        </td>
                        <td>
                            {this.state.showRoute && this.smallRoute.map(routeUnit => {
                                return (
                                    <div key={routeUnit[0]}>
                                        Остановка {routeUnit[0]} <br/>
                                        &#10240;&#10240;&#10240;&#10240;&#10240;&#10240;&#10240;&#10240;&#8615; - {routeUnit[2]}<br/>
                                        Остановка {routeUnit[1]}
                                    </div>
                                );
                            })}
                            <br/>
                            {this.state.showRoute && this.responseJson[2].map(limitUnit => {
                                return (
                                    <div key={limitUnit.transport}>
                                        {limitUnit.transport} <br/>
                                        Будет потрачено: {limitUnit.spent}
                                    </div>
                                );
                            })}
                            <br/>
                            {!this.state.enoughLim && this.state.showRoute && <div className="badAlert" >Недостаточно лимитов для совершения поездки</div>}
                            {this.state.enoughLim && <div className="goodAlert" >Достаточно лимитов для совершения поездки</div>}
                            <form>
                                <div>
                                    Stoppage from:<br/>
                                    <InputText name="from" keyfilter="pint" onChange={this.handleChange} value={this.state.from} />
                                </div>
                                <div>
                                    Stoppage to:<br/>
                                    <InputText name="to" keyfilter="pint" onChange={this.handleChange} value={this.state.to} />
                                </div>
                            </form>
                            {this.state.wrongStpg && <div className="badAlert">Введены неверные номера остановок</div>}
                            <Button label="Показать маршрут" icon="pi pi-check" onClick={this.showRoute} />&#10240;
                            <Button label="Проехать" disabled={!this.state.enoughLim || this.state.block} icon="pi pi-check" onClick={this.takeRide} />
                            <br/><br/>
                            {this.state.succRide && <div className="goodAlert">Поездка успешно совершена</div>}
                        </td>
                    </tr>
                </table>
            </center>
        )
    }
}
