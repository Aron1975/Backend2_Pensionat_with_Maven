package com.backend2.backend2_pensionat_with_maven.Security;

/*@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepo passwordResetTokenRepo;

    public void createPasswordResetTokenForUser(User user, String passwordToken){
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepo.save(passwordResetToken);
    }

    public String validatePasswordResetToken(String theToken) {
        PasswordResetToken token = passwordResetTokenRepo.findByToken(theToken);
        if (token == null){
            return "Invalid password reset token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime()-calendar.getTime().getTime()) <= 0){
            return "Link already expired, resend link";
        }
        return "valid";
    }

    public Optional<User> findUserByPasswordToken(String passwordToken){
        return Optional.ofNullable(passwordResetTokenRepo.findByToken(passwordToken).getUser());
    }

}*/
