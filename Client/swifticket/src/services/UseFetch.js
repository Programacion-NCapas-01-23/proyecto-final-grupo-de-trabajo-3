import { useState, useEffect } from 'react'
import axios from 'axios';

const BASE_URL = 'http://localhost:8080/';

export function useFetchLogin (_url) {
    const [loading, setLoading] = useState(true)
    const [data, setData] = useState(undefined)
    const [error, setError] = useState(null)
    const [controller, setController] = useState(null)

    useEffect(() => {
        const abortController = new AbortController()
        setController(abortController)
        setLoading(true)

        axios({
            method: 'GET',
            baseURL: BASE_URL,
            url: _url,
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
            }
          })
          .then(response => {
            setData(response.data);
          })
          .catch(error => {
            if (error.name === "AbortError")
                console.log(error);
            else
                setError(error.response.data)
          })
          .finally(() => setLoading(false));

        // Funcion de limpieza
        return () => abortController.abort()
    }, [])

    const handleCancelRequest = () => {
        if (controller) {
            controller.abort();
            setError("AbortError")
        }
    }

    return { data, loading, error, handleCancelRequest }
}
