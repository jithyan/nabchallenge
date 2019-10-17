import React from 'react';

import Selector from '../Selector/Selector';

const App: React.FC = () => {
    return (
        <div className="ui container">
            <h1 className="ui header">Crypto Currency Trading Daily Profit Maximizer</h1>
            <h3>Analyzes historical price data for crypto currencies to determine
                the best buy and sell times for a day.
            </h3>
            <Selector />
        </div>
    );
};

export default App;