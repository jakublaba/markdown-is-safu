import React, {useState} from "react";

import * as Yup from "yup";
import {ErrorMessage, Field, Form, Formik} from "formik";
import {Button, Spinner} from "react-bootstrap";
import {useDispatch} from "react-redux";
import {Dispatch} from "redux";
import {IAction} from "../types/types";
import {useNavigate} from "react-router-dom";


interface IRegisterFormData {
    username: string,
    email: string,
    password: string,
    repeatPassword: string
}

const Register: React.FC = () => {
    const dispatch = useDispatch<Dispatch<IAction>>();
    const navigate = useNavigate();
    const [loading, setLoading] = useState<boolean>(false);
    const [passwordVisible, setPasswordVisible] = useState<boolean>(false);

    const initialValues: IRegisterFormData = {
        username: "",
        email: "",
        password: "",
        repeatPassword: ""
    };

    const validationSchema = Yup.object().shape({
        username: Yup.string()
            .required("Required field")
            .min(4, "Minimum 4 characters required")
            .max(32, "Maximum 32 characters allowed")
            .matches(
                /^[a-zA-Z0-9_-]+$/,
                "Special characters besides _ and - not allowed"
            ),
        email: Yup.string()
            .required("Required field")
            .min(4, "Minimum 4 characters required")
            .max(64, "Maximum 64 characters allowed")
            .email("This is not a valid email"),
        password: Yup.string()
            .required("Required field")
            .min(8, "Minimum 8 characters required")
            .max(128, "Maximum 128 characters allowed")
            .matches(
                /^(?=(.*[a-z])+)(?=(.*[A-Z])+)(?=(.*\d)+)(?=(.*[!@#$%^&*()\-_+.])).{4,}$/,
                "Must contain at least 1 of each: lowercase character, uppercase character, digit, special character"
            ),
        repeatPassword: Yup.string()
            .required("Required field")
            .test(
                "matches",
                "Passwords don't match",
                function (repeatPass: any) {
                    return this.parent.password === repeatPass;
                }
            )
    });

    const handleRegister = async (formValue: IRegisterFormData) => {
        setLoading(true);
        const {username, email, password} = formValue;

        const sleep = (ms: number) => new Promise((r) => setTimeout(r, ms));
        await sleep(1000);
        setLoading(false);
        dispatch({
            type: "login"
        });
        navigate("/");
    };

    const togglePassword = () => {
        setPasswordVisible(!passwordVisible);
    };

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={handleRegister}
        >
            <Form>
                <Field
                    name={"username"}
                    type={"text"}
                    placeholder={"Username"}
                    className={"form-control"}
                />
                <ErrorMessage
                    name="username"
                    component="div"
                    className="alert alert-danger"
                />

                <Field
                    name={"email"}
                    type={"email"}
                    placeholder={"Email address"}
                    className={"form-control"}
                />
                <ErrorMessage
                    name="email"
                    component="div"
                    className="alert alert-danger"
                />

                <Field
                    name={"password"}
                    type={passwordVisible ? "text" : "password"}
                    placeholder={"Password"}
                    className={"form-control"}
                />
                <ErrorMessage
                    name="password"
                    component="div"
                    className="alert alert-danger"
                />

                <Field
                    name={"repeatPassword"}
                    type={passwordVisible ? "text" : "password"}
                    placeholder={"Repeat password"}
                    className={"form-control"}
                />
                <ErrorMessage
                    name="repeatPassword"
                    component="div"
                    className="alert alert-danger"
                />

                <Button type="submit" className="btn btn-primary btn-block" disabled={loading}>
                    {loading
                        ? <Spinner as={"span"}></Spinner>
                        : "Sign up"
                    }
                </Button>
                <Button type={"button"} onClick={togglePassword}>
                    {passwordVisible ? "Hide password" : "Show password"}
                </Button>
            </Form>
        </Formik>
    );
};

export default Register;