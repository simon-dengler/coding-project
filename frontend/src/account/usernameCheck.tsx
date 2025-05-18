import { Button, TextField, Container, Typography, Box } from "@mui/material";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";
import baseApi from "../api/baseApi";
import {useNavigate, useParams} from "react-router-dom";

export default function UsernameCheck() {
    const [username, setUsername] = useState("");
    const navigate = useNavigate();

    const handleTextChange = (event: ChangeEvent<HTMLInputElement>) => {
        setUsername(event.target.value);
    };

    const handleSubmitFormData = (event: FormEvent<HTMLElement>) => {
            event.preventDefault();
            baseApi.postData("check", {username: username}).then(exists => {
                if (exists){
                    navigate("/login/" + username);
                } else {
                    navigate("/register/" + username);
                }
            });
        };

    const createNewUser = (event: FormEvent<HTMLElement>) => {
        event.preventDefault();
        navigate("/register/" + username);
    };

    return (
        <Container maxWidth="sm">
            <Box sx={{ my: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Enter username
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
                    '& > :not(style)': { m: 1, width: '25ch' },
                }}
            >
                <TextField label="Username"
                    name="username"
                    value={username}
                    required
                    onChange={handleTextChange}
                />

                <Box sx={{ float: "right" }}>
                    <Button type="button" onClick={createNewUser} sx={{ mr: 1 }}>
                        Create New
                    </Button>
                    <Button type="submit" variant="contained">
                        Submit
                    </Button>
                </Box>
            </Box>
        </Container>
    );
}