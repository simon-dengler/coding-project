import {ErrorData} from "../form/form";


class BaseApi {
    private getToken(): string | null {
        return localStorage.getItem("jwt");
    }

    private getHeaders(): HeadersInit {
        const token = this.getToken();
        return {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': `Bearer ${token}` })
        };
    }
    
    private readonly baseApiUrl = "http://localhost:8080/api";

    async getData(apiUri: string): Promise<unknown> {
        const url = `${this.baseApiUrl}/${apiUri}`;
        const headers = this.getHeaders();
        return fetch(url, {
            method: 'GET',
            headers: headers
        }).then(response => !response.ok
            ? Promise.reject(response)
            : response.json()
        ).catch(error => {
            /*error.json().then((data: ErrorData) => {
                console.error(data);
                alert(data.error + ": " + data.message);
            });*/
            console.error(error);
        });
    }

    async postData(apiUri: string, data: unknown): Promise<unknown> {
        const url = `${this.baseApiUrl}/${apiUri}`;
        const headers = this.getHeaders();
        return fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: headers
        }).then(response => !response.ok
            ? Promise.reject(response)
            : response.json()
        ).catch(error => {
            error.json().then((data: ErrorData) => {
                console.error(data);
                alert(data.error + ": " + data.message);
            });
            return Promise.reject(error);
        });
    }
}

export default new BaseApi();