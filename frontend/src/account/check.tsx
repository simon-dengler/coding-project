import {Button, TextField, Container, Typography, Box} from "@mui/material";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import baseApi from "../api/baseApi";
import {useLocation, useNavigate, useParams} from "react-router-dom";

export default function Check() {
    const [username, setUsername] = useState("");
    const navigate = useNavigate();
    const location = useLocation();
    const fromBefore = location.state?.from?.pathname || "/";

    const handleTextChange = (event: ChangeEvent<HTMLInputElement>) => {
        setUsername(event.target.value);
    };

    const handleSubmitFormData = (event: FormEvent<HTMLElement>) => {
            event.preventDefault();
            baseApi.postData("auth/check", {username: username})
            .then(body => {
                const response = body as {exists: boolean};
                if (response.exists==true){
                    navigate("/login/" + username, {
                        state: {from:fromBefore}
                    });
                } else {
                    navigate("/register/" + username, {
                        state: {from:fromBefore}
                    });
                }
            })
            .catch(errorData => {
                console.error(errorData.error);
            });
        };

    return (
        <Container maxWidth="sm">
            <Box sx={{my: 4}}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Log In / Sign up
                </Typography>
                <Typography>
                    Please enter your username.
                </Typography>
            </Box>
            <Box component="form"
                noValidate
                autoComplete="off"
                onSubmit={handleSubmitFormData}
                sx={{
                    '& > :not(style)': {m: 1, width: '25ch'},
                }}
            >
                <TextField label="Username"
                    name="username"
                    value={username}
                    required
                    onChange={handleTextChange}
                />

                <Box sx={{float: "right"}}>
                    <Button type="button" onClick={()=>{navigate("/");}} sx={{mr: 1}}>
                        Cancel
                    </Button>
                    <Button type="submit" variant="contained">
                        Submit
                    </Button>
                </Box>
            </Box>
        </Container>
    );
}
