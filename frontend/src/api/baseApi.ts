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
        }).then(async response => {
            const contentType = response.headers.get("Content-Type") || "";
            if(!response.ok) {
                if (contentType.includes("application/json")) {
                    const errorData: ErrorData = await response.json();
                    return await Promise.reject(errorData);
                } else {
                    const errorText: string = await response.text();
                    return Promise.reject({error: errorText, message: errorText});
                }     
            }
            return response.json();
        });
    }

    async postData(apiUri: string, data: unknown): Promise<unknown> {
        const url = `${this.baseApiUrl}/${apiUri}`;
        const headers = this.getHeaders();
        return fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: headers
        }).then(async response => {
            const contentType = response.headers.get("Content-Type") || "";
            if(!response.ok) {
                if (contentType.includes("application/json")) {
                    const errorData: ErrorData = await response.json();
                    return await Promise.reject(errorData);
                } else {
                    const errorText: string = await response.text();
                    return Promise.reject({error: errorText, message: errorText});
                }     
            }
            return response.json();
        });
    }
}

export default new BaseApi();