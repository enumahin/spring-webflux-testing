job('First-Java-Compilation-DSL'){
    description("First Maven job generated bt the DSL on ${new Date()}")
    scm{
        git("https://github.com/enumahin/spring-webflux-testing.git", 'master')
    }
    triggers{
        scm("* * * * *")
    }
    steps{
        maven("clean package")
    }
    publishers {
        // archive the war file generated
        archiveArtifacts 'target/*.jar'
    }
}