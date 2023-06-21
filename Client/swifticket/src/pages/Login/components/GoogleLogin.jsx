import { useRef, useEffect } from 'react';

// https://github.com/anthonyjgrove/react-google-login/issues/502
// https://developers.google.com/identity/gsi/web/reference/js-reference#CredentialResponse

const useScript = (url, onload) => {
  useEffect(() => {
    const script = document.createElement('script');

    script.src = url;
    script.onload = onload;

    document.head.appendChild(script);

    return () => {
      document.head.removeChild(script);
    };
  }, [url, onload]);
};

export default function GoogleLogin({ onGoogleSignIn = () => {} }) {
  const googleSignInButton = useRef(null);

  useScript('https://accounts.google.com/gsi/client', () => {
    // https://developers.google.com/identity/gsi/web/reference/js-reference#google.accounts.id.initialize
    window.google.accounts.id.initialize({
      client_id:
        '893111957431-h36mol3osmc1ajq441slto5mrha4vv9i.apps.googleusercontent.com',
      callback: onGoogleSignIn,
    });
    // https://developers.google.com/identity/gsi/web/reference/js-reference#google.accounts.id.renderButton
    window.google.accounts.id.renderButton(
      googleSignInButton.current,
      {
        type: 'icon',
        theme: 'outline',
        size: 'large',
        text: 'continue_with',
        shape: 'pill',
      } // customization attributes
    );
  });

  return <div ref={googleSignInButton}></div>;
}
