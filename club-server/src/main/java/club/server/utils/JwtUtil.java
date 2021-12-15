package club.server.utils;
import club.common.utils.JsonTimestampUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

public class JwtUtil {
    private static String pwd = "password";
    /**
     Issued to: the id of this user
     Issuing time: now
     Effective time: 30 minutes
     Load content: Temporarily designed as: this person's name, this person's nickname
     Encryption key: the person’s id plus a string
     */
    public static String createToken(String userId,Integer type,String info) {
        Calendar nowTime = Calendar.getInstance();
        //Expires in 30 minutes
        nowTime.add(Calendar.MINUTE,30);
        Date expiresDate = nowTime.getTime();
        return JWT.create().withAudience(userId)   //Issuing object
                .withIssuedAt(new Date())    //publish time
                .withExpiresAt(expiresDate)  //Effective time
                .withClaim("info", info)
                .withClaim("userId", userId)
                .withClaim("type", type)
                .sign(Algorithm.HMAC256(pwd));   //encryption
    }

    /**
     * Check the legitimacy, where the secret parameter should be passed in the user id
     * @param token
     */
    public static DecodedJWT verifyToken(String token){
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(pwd)).build();
            jwt = verifier.verify(token);
            System.out.println("Expiration time："+jwt.getExpiresAt());
            //Verify that user has clicked to log out
            if (!LogoutUtil.get(jwt.getClaim("userId").asString()+JsonTimestampUtil.getTimestamp(jwt.getExpiresAt()))) {
                return jwt;
            }
        } catch (Exception e) {
            //Failed validation
            e.printStackTrace();

        }
        return null;
    }
    /**
     * Get the DecodedJWT object after successful verification
     */
    public static DecodedJWT getDecodedJWT(String token){
        DecodedJWT jwt = null;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(pwd)).build();
        jwt = verifier.verify(token);
        return jwt;
    }
    /**
     * Get the issuance object
     */
    public static String getAudience(String token){
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            //token parsing failed
            j.printStackTrace();
        }
        return audience;
    }


    /**
     * Get the value of the payload by the payload name
     */
    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }
}