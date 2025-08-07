import { Stack } from "expo-router";

export default function RootLayout(){
    return(
        <Stack>
            <Stack.Screen name="autenticacao/login" options={{headerShown: false}}/>
        </Stack>

    )
}