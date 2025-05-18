import {Typography, Link, Container, Box} from "@mui/material";
import {styled} from "@mui/material/styles";

const footerStyle = {
    display: "flex",
    justifyContent: "center",
    alignContent: "align-center",
    backgroundColor: "#556cd6",
};
const StyledFooterBox = styled(Box)(({theme}) => ({
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    margin: theme.spacing(2),
}));


function Copyright() {
    return (
        <Typography variant="body2" color="text.secondary" align="center">
            Copyright ©
            <Link color="inherit" href="https://www.key-work.de/team-anwendungsentwicklung/">
                Key-Work
            </Link>&nbsp;
            {new Date().getFullYear()}
            .
        </Typography>
    );
}

export default function Footer(){
    return(
        <Container maxWidth="xl" sx={footerStyle}>
            <StyledFooterBox>
                <Box
                    component="img"
                    src="/key-work.svg"
                    alt="Logo"
                    sx={{height: 40}} 
                />
            </StyledFooterBox>
            <StyledFooterBox>
                <Copyright />
            </StyledFooterBox>

        </Container>

    );
}