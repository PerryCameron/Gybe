package com.ecsail.Gybe.service;

//@Service
public class CustomUserDetailsService   {
//public class CustomUserDetailsService implements UserDetailsService  {
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public CustomUserDetailsService(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        String userSQL = "SELECT username, password, enabled FROM users WHERE username = ?";
//        UserDTO userDTO = jdbcTemplate.queryForObject(userSQL, new Object[]{username}, (rs, rowNum) -> new UserDTO(
//                rs.getString("username"),
//                rs.getString("password"),
//                rs.getBoolean("enabled")
//        ));
//
//        String authoritiesSQL = "SELECT authority FROM authorities WHERE username = ?";
//        List<GrantedAuthority> authorities = jdbcTemplate.query(authoritiesSQL, new Object[]{username},
//                (rs, rowNum) -> {
//                    AuthorityDTO authority = new AuthorityDTO();
//                    authority.setAuthority(rs.getString("authority"));
//                    return authority;
//                });
//
//        userDTO.setAuthorities(authorities);
//
//        if (userDTO.isActive()) {
//            return new org.springframework.security.core.userdetails.User(
//                    userDTO.getUsername(),
//                    userDTO.getPassword(),
//                    userDTO.getAuthorities());
//        } else {
//            throw new DisabledException("User is not active");
//        }
//    }
}
