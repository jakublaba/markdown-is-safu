import {IAction, IAuthState} from "../types/types";

const unauthenticated: IAuthState = {
    authenticated: false,
    jwt: undefined
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
