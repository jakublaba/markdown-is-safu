import React from 'react';
import './styles/App.css';
import {useDispatch, useSelector} from "react-redux";
import {IAction, IAuthState} from "./types/types";


const App: React.FC = () => {
    const authState = useSelector<IAuthState, IAuthState>(authState => authState);
    const dispatch = useDispatch();

    const logout = () => {
        dispatch<IAction>({
            type: "logout"
        });
    };

    return (
        <div>
            
        </div>
    );
};

export default App;
