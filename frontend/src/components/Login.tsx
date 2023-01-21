import React from "react";
import {useDispatch} from "react-redux";
import {IAction} from "../types/types";
import {Dispatch} from "redux";
import {Button} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

const Login: React.FC = () => {
    const dispatch = useDispatch<Dispatch<IAction>>();
    const navigate = useNavigate();

    const login = () => {
        dispatch({
            type: "login"
        });
        navigate("/");
    };

    return (
        <div>
            <h1>
                Login
            </h1>
            <Button onClick={login}>
                Login
            </Button>
        </div>
    );
};

export default Login;
