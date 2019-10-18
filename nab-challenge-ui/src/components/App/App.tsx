import React from 'react';

import Selector from '../Selector'
import Result from '../Result';

const show = false;

const App: React.FC = () => {
    return (
        <div className="ui container">
            <h1 className="ui header">Crypto Currency Trading Daily Profit Maximizer</h1>
            <h3>Analyzes historical price data for crypto currencies to determine
                the best buy and sell times for a day.
            </h3>
            {show && <Result/>}
            {!show && <Selector />}
        </div>
    );
};

export default App;