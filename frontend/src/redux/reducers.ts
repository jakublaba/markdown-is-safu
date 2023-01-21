import {IAction, IAuthState} from "../types/redux-types";

const unauthenticated: IAuthState = {
    authenticated: false,
    jwt: ""
};

const rootReducer = (
    state: IAuthState | undefined,
    action: IAction
): IAuthState => {
    switch (action.type) {
        case "logout":
            return unauthenticated;
        case "login":
            return {
                authenticated: true,
                jwt: action.jwt
            };
        default:
            return unauthenticated;
    }
};

export default rootReducer;
