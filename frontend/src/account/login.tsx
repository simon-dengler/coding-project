import {Container, TextField, Box, Button, Typography} from "@mui/material";
import {ChangeEvent, FormEvent, useState} from "react";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import baseApi from "../api/baseApi";

interface FormData {
    username: string,
    password: string
}

export default function Login(){
    const location = useLocation();
    const navigate = useNavigate();
    const userPrefill = useParams().username as string;
    const [formData, setFormData] = useState<Partial<FormData>>({username: userPrefill});
    const from = location.state?.from?.pathname || "/";

    const handleTextChange = (event: ChangeEvent<HTMLInputElement>) => {
        const value = event.target.value;
        const name = event.target.name;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmitFormData = (event: FormEvent<HTMLElement>) => {
        event.preventDefault();
        baseApi.postData("auth/login", formData)
            .then(body => {
                const response = body as {token: string};
                localStorage.setItem("jwt", response.token);
                navigate(from, {replace: true});
            })
            .catch(() =>{
                alert("Something went wrong. Please try again.");
            });
    };

    return (
        <Container maxWidth="sm">
            <Box sx={{my: 4}}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Login
                </Typography>
                <Typography>
                    Enter your username and password.  
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
                <div>
                    <TextField label="Username"
                        name="username"
                        value={formData.username ?? ""}
                        required
                        onChange={handleTextChange}
                    />
                </div>
                <div>
                    <TextField label="Password"
                        name="password"
                        type="password"
                        value={formData.password ?? ""}
                        required
                        onChange={handleTextChange}
                    />
                </div>       
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
