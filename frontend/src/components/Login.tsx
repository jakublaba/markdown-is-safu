import React, {useState} from "react";
import {useDispatch} from "react-redux";
import {IAction} from "../types/types";
import {Dispatch} from "redux";
import {Button, Spinner} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {ErrorMessage, Field, Form, Formik} from "formik";
import * as Yup from "yup";

interface ILoginFormData {
    username: string,
    password: string
}

const Login: React.FC = () => {
    const dispatch = useDispatch<Dispatch<IAction>>();
    const navigate = useNavigate();
    const [loading, setLoading] = useState<boolean>(false);
    const [passwordVisible, setPasswordVisible] = useState<boolean>(false);

    const initialValues: ILoginFormData = {
        username: "",
        password: ""
    };

    const validationSchema = Yup.object().shape({
        username: Yup.string().required("Required field"),
        password: Yup.string().required("Required field")
    });

    const login = async (formData: ILoginFormData) => {
        setLoading(true);
        //const {username, password} = formData;

        // TODO - for now processing login request is simulated with sleep
        const sleep = (ms: number) => new Promise((r) => setTimeout(r, ms));
        await sleep(1000);
        dispatch({
            type: "login"
        });
        navigate("/");
    };

    const togglePassword = () => {
        setPasswordVisible(!passwordVisible);
    }

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={login}
        >
            <Form>
                <Field
                    name={"username"}
                    type={"text"}
                    placeholder={"Username"}
                    className={"form-control"}
                />
                <ErrorMessage
                    name={"username"}
                    component={"div"}
                    className={"alert alert-danger"}
                />

                <Field
                    name={"password"}
                    type={passwordVisible ? "text" : "password"}
                    placeholder={"Password"}
                    className="form-control"
                />
                <ErrorMessage
                    name={"password"}
                    component={"div"}
                    className={"alert alert-danger"}
                />

                <Button type={"submit"} disabled={loading}>
                    {loading
                        ? <Spinner as={"span"}/>
                        : "Login"
                    }
                </Button>

                <Button onClick={togglePassword}>
                    {passwordVisible ? "Hide password" : "Show password"}
                </Button>
            </Form>
        </Formik>
    );
};

export default Login;
