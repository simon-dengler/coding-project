import {useNavigate} from "react-router-dom";
import {Box, Typography, Container, Button} from "@mui/material";

export default function Account() {
    const username = localStorage.getItem("username");
    const navigate = useNavigate();

    const logout = () => {
        localStorage.removeItem("jwt");
        localStorage.removeItem("username");
        navigate("/");
    };
    
    return(
        <Container maxWidth="sm">
            <Box sx={{my: 4}}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Your Account
                </Typography>
                <Typography>
                    Welcome to your account, {username}.  
                </Typography>
            </Box>
            <Box sx={{float: "right"}}>
                <Button type="button" onClick={logout}>
                    Logout
                </Button>
            </Box>
        </Container>
    );
}
