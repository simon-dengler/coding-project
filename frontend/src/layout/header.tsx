import {AppBar, Toolbar, Button, Box, Link, Container} from "@mui/material";
import {Link as RouterLink} from "react-router-dom";
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';

const navbarStyle={
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'align-center',
    mr: 1
};

const navbarStartStyle={
    display: 'flex',
    justifyContent: 'flex-start',
    alignItems: 'align-center',
    
};

const navbarEndStyle={
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'align-center',
    
};

const accountIconStyle={
    fontSize: 48,
    color: "#004986",
    cursor: "pointer",
    mt: 1,
};

const navLinks = [
    {label:"Home", to:"/"},
    {label:"Form", to:"/form"},
];

function Header() {
    return(
        <AppBar position="static">
            <Container maxWidth="xl">
                <Toolbar sx={navbarStyle}>
                    <Box sx={navbarStartStyle}>
                        <Link href="https://www.key-work.de/" sx={{mt: 1, mr: 1}}>
                            <Box
                                component="img"
                                src="/key-work.svg"
                                alt="Logo"
                                sx={{height: 40}}
                            />
                        </Link>
                        {navLinks.map(({label, to}) => (
                            <Button
                                key={to}
                                component={RouterLink}
                                to={to}
                                color="inherit"
                                sx={{mt: 1, color: "#004986"}}
                            >
                                {label}
                            </Button>
                        ))}
                    </Box>
                    <Box sx={navbarEndStyle}>
                        <Box>
                        <Link component={RouterLink} to="/account" sx={{mt: 1, mr: 1, height: 4}}>
                            <AccountCircleOutlinedIcon 
                                sx={accountIconStyle}    
                            />
                        </Link>
                        </Box>
                    </Box>
                </Toolbar>
            </Container>
        </AppBar>    
    );
}

export default Header;