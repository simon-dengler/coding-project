import Header from "./header";
import Footer from "./footer";
import {Container, Box} from "@mui/material";
import {Outlet} from "react-router-dom";

function Layout(){
    return(
        <Box 
            display="flex"
            flexDirection="column"
            minHeight="100vh"
        >            
            <Header />
            <Container component="main" sx={{flex: 1}}>
                <Outlet />
            </Container>
            <Footer />
        </Box>
    );
}

export default Layout;