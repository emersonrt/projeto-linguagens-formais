import model.AutomatoFinito;
import service.AutomatoDigestService;
import utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class main {

    //    M = (E, T, δ, S, F)
    //    E = {S, A, B, X}
    //    T = {a, b}
    //    F = {X}
    //    δ(S,a) = {A}
    //    δ(S,b) = {B}
    //    δ(A,a) = {A}
    //    δ(A,b) = {B}
    //    δ(B,b) = {B, X}

    public static void main(String[] args) {

        AutomatoDigestService service = new AutomatoDigestService();

        List<String> texto = new ArrayList<>();
        texto.add("M=({S,A,B,X},{a,b},δ,s,{X})");
        texto.add("δ(S,a) = {A}");
        texto.add("δ(S,b) = {B}");
        texto.add("δ(A,a) = {A}");
        texto.add("δ(A,b) = {B}");
        texto.add("δ(B,b) = {B,X}");

        AutomatoFinito af = service.lerAutomatoFinito(texto);



    }

}
