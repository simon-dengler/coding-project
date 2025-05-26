import {useEffect} from "react";
import {Outlet, useLocation, useNavigate,} from "react-router-dom";
import baseApi from "../api/baseApi";

export default function RouteProtection(){
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        if(localStorage.getItem("jwt")){
            baseApi.getData("account")
            .then(data => {
                const response = data as {username: string};
                localStorage.setItem("username", response.username);
            })
            .catch(errorData => {
                console.log(errorData.error);
                localStorage.removeItem("jwt");
                navigate("/check", {
                    replace: true,
                    state: {from:location}
                });
            });
        } else {
            navigate("/check", {
                replace: true,
                state: {from:location}
            });
        }
    }, []);

    // Possibly flash of protected content, but no loading time on every request. Also data is protected by backend.
    return (
        <Outlet />
    );
}
