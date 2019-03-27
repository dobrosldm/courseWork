import React from 'react';
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";

export default class Limits extends React.Component {

    constructor(props) {
        super(props);
        this.state = { from: '', to: '', howMuchTo: '', limits : []};
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const {from, to, howMuchTo} = this.state;
        fetch('api/limits/convert', {
            method: 'post',
            headers: {'Content-Type':'application/json'},
            body: JSON.stringify({
                from: from,
                to: to,
                howMuchTo: howMuchTo
            })
        }).then(response => {
            return response.text();
        }).then(data => {
            alert(data);
            this.updateLimits();
        })
    };

    componentWillMount() {
        this.updateLimits();
    }

    updateLimits = () => {
        fetch('http://localhost:24315/api/limits')
            .then(results => {
                return results.json();
            }).then(data => {
            this.setState({limits: data});
        })
    };

    render() {
        const {from, to, howMuchTo} = this.state;
        return (
            <div align="center">
                <div>
                    {
                        this.state.limits.map(limit => {
                            return (
                                <div key={limit.transport}>
                                    {limit.transport} {limit.limit}
                                </div>
                            )
                        })
                    }
                </div>
                <br/>
                <form onSubmit={this.handleSubmit}>
                    <div>
                        From:<br/>
                        <InputText name="from" onChange={this.handleChange} value={from}/>
                    </div>
                    <div>
                        To:<br/>
                        <InputText name="to" onChange={this.handleChange} value={to}/>
                    </div>
                    <div>
                        How much to gain:<br/>
                        <InputText name="howMuchTo" onChange={this.handleChange} value={howMuchTo}/>
                    </div>
                    <Button type='submit' label='Convert' icon='pi pi-check' />
                </form>
            </div>
        )
    }
}