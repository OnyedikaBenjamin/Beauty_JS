import React from "react";
import {
  AboutSection,
  ArticlesSection,
  ContactSection,
  HeroSection,
  InterestsSection,
  Page,
  ProjectsSection,
  Seo,
} from "gatsby-theme-portfolio-minimal";

export default function IndexPage() {
  return (
    <>
      <Seo title="Isah Jacob" />
      <Page useSplashScreenAnimation>
        <HeroSection sectionId="hero" />
        <AboutSection sectionId="about" heading="About Me" />
        <ArticlesSection sectionId="articles" heading="Latest Articles" sources={['Medium']} />
        <InterestsSection sectionId="details" heading="My Toolbox" />
        <ProjectsSection sectionId="Projects" heading="Projects" />
        <ContactSection sectionId="contact" heading="Contact Me" />
      </Page>
    </>
  );
}
