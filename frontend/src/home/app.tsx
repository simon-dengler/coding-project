import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import {Button} from "@mui/material";
import {Link as RouterLink} from "react-router-dom";

export default function App() {
    return (
        <Container maxWidth="sm">
            <Box sx={{my: 4}}>
                <Typography variant="h4" component="h1" gutterBottom>
                   Lottery
                </Typography>
                <Typography>
                    Win a price by submitting your personal data and beating a simple game.
                </Typography>
            </Box>
            <Box sx={{display: "flex", flexDirection: "row-reverse", mb: 5}}>
                <Button variant="contained" component={RouterLink} to="/form">
                    start
                </Button>
            </Box>
        </Container>
    );
}