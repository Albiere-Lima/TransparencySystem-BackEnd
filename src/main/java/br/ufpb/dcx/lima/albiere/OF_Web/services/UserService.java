package br.ufpb.dcx.lima.albiere.OF_Web.services;

import br.ufpb.dcx.lima.albiere.OF_Web.dtos.ChangePasswordDTO;
import br.ufpb.dcx.lima.albiere.OF_Web.dtos.UserNotificationsDTO;
import br.ufpb.dcx.lima.albiere.OF_Web.dtos.UserResponseDTO;
import br.ufpb.dcx.lima.albiere.OF_Web.models.User;
import br.ufpb.dcx.lima.albiere.OF_Web.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(User user) {
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhone(),
                user.getRegistrationNumber(),
                user.getCampus(),
                user.getDepartment(),
                user.getBio(),
                user.getRole(),
                user.getInitials(),
                user.getAvatarColor(),
                user.getPicture(),
                user.getPassword(),
                user.getNotifyDailySummary(),
                user.getNotifyMonthlyReports(),
                user.getNotifyNewExpenses(),
                user.getNotifySystemAlerts()
        );
    }

    @Transactional
    public UserResponseDTO updateNotifications(String email, UserNotificationsDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail: " + email));

        if (dto.notifyDailySummary() != null) user.setNotifyDailySummary(dto.notifyDailySummary());
        if (dto.notifyMonthlyReports() != null) user.setNotifyMonthlyReports(dto.notifyMonthlyReports());
        if (dto.notifyNewExpenses() != null) user.setNotifyNewExpenses(dto.notifyNewExpenses());
        if (dto.notifySystemAlerts() != null) user.setNotifySystemAlerts(dto.notifySystemAlerts());

        return convertToDTO(userRepository.save(user));
    }

    @Transactional
    public void changePassword(String email, ChangePasswordDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail: " + email));

        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("A senha atual informada está incorreta.");
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        convertToDTO(userRepository.save(user));
    }
}