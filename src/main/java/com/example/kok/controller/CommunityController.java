package com.example.kok.controller;

import com.example.kok.aop.aspect.annotation.LogReturnStatus;
import com.example.kok.auth.CustomUserDetails;
import com.example.kok.dto.AdvertisementDTO;
import com.example.kok.dto.CompanyDTO;
import com.example.kok.dto.ExperienceNoticeDTO;
import com.example.kok.enumeration.UserRole;
import com.example.kok.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityPostService communityPostService;
    private final ExperienceNoticeService experienceNoticeService;
    private final MemberService memberService;
    private final AdvertisementService advertisementService;
    private final CompanyService companyService;

    @GetMapping("/page")
    @LogReturnStatus
    public String goToCommunityPage(Model model,
                                    @RequestParam(required = false) String sharedPostId,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if(sharedPostId!=null){
            model.addAttribute("sharedPostId", sharedPostId);
            System.out.println("sharedPostId: "+sharedPostId);
        }
        Long memberId = null;

        if (customUserDetails != null) {
            memberId = customUserDetails.getId();

            if (customUserDetails.getUserRole() == UserRole.COMPANY) {
                CompanyDTO companyDTO = companyService.findCompanyById(customUserDetails.getId());
                model.addAttribute("companyDTO", companyDTO);
            }

            memberService.findMembersByMemberId(memberId)
                    .ifPresent(userMemberDTO -> model.addAttribute("member", userMemberDTO));
        }

        model.addAttribute("posts", communityPostService.getList(1, memberId).getPosts());

        List<ExperienceNoticeDTO> latestFourExperience = experienceNoticeService.findLatestFourExperience();
        model.addAttribute("latestFour", latestFourExperience);

        List<AdvertisementDTO> advertisements = advertisementService.getAllAdvertisements();
        model.addAttribute("advertisements", advertisements);

        return "community/page";
    }

}
